package poseidon.lib.core.tool.sql;

import java.sql.*;
import java.io.*;
import java.math.*;
import java.util.*;
import java.text.*;
import java.lang.reflect.*;

public class DebuggableStatement {


	private String filteredSql; // statement filtered for rogue '?' that are not
								// bind variables.
	private DebugObject[] variables; // array of bind variables
	private SqlFormatter formatter; // format for dates
	private long startTime; // time that statement began execution
	private long executeTime; // time elapsed while executing statement

	private DebuggableStatement( String sql, SqlFormatter formatter) throws SQLException {
		this.formatter = formatter;

		// see if there are any '?' in the statement that are not bind variables
		// and filter them out.
		boolean isString = false;
		char[] sqlString = sql.toCharArray();
		for (int i = 0; i < sqlString.length; i++) {
			if (sqlString[i] == '\'')
				isString = !isString;
			// substitute the ? with an unprintable character if the ? is in a
			// string.
			if (sqlString[i] == '?' && isString)
				sqlString[i] = '\u0007';
		}
		filteredSql = new String(sqlString);

		// find out how many variables are present in statement.
		int count = 0;
		int index = -1;
		while ((index = filteredSql.indexOf("?", index + 1)) != -1) {
			count++;
		}

		// create array for bind variables
		variables = new DebugObject[count];

		
	}


	/**
	 * Tests Object o for parameterIndex (which parameter is being set) and
	 * places object in array of variables.
	 * 
	 * @param parameterIndex
	 *            which PreparedStatement parameter is being set. Sequence
	 *            begins at 1.
	 * @param o
	 *            Object being stored as parameter
	 * @exception Thrown
	 *                if index exceeds number of variables.
	 */
	private void saveObject(int parameterIndex, Object o) throws ParameterIndexOutOfBoundsException {
		if (parameterIndex > variables.length)
			throw new ParameterIndexOutOfBoundsException("Parameter index of " + parameterIndex + " exceeds actual parameter count of " + variables.length);

		variables[parameterIndex - 1] = new DebugObject(o);
	}

	/**
	 * Adds name of the Array's internal class type(by using
	 * x.getBaseTypeName()) to the debug String. If x is null, NULL is added to
	 * debug String.
	 * 
	 * @param i
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 */
	public void setArray(PreparedStatement ps, int i, java.sql.Array x) throws SQLException {
		saveObject(i, x);
	}

	/**
	 * Debug string prints NULL if InputStream is null, or adds
	 * "stream length = " + length
	 */
	public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
		saveObject(parameterIndex, (x == null ? "NULL" : "<stream length= " + length + ">"));
	}

	/**
	 * Adds BigDecimal to debug string in parameterIndex position.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 */
	public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
		saveObject(parameterIndex, x);
	}

	/**
	 * Debug string prints NULL if InputStream is null, or adds
	 * "stream length= " + length.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 * @param length
	 *            length of InputStream
	 */
	public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
		saveObject(parameterIndex, (x == null ? "NULL" : "<stream length= " + length + ">"));
	}

	/**
	 * Adds name of the object's class type(Blob) to the debug String. If object
	 * is null, NULL is added to debug String.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 */
	public void setBlob(int parameterIndex, Blob x) throws SQLException {
		saveObject(parameterIndex, x);
	}

	/**
	 * Adds boolean to debug string in parameterIndex position.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 */
	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		saveObject(parameterIndex, new Boolean(x));
	}

	/**
	 * Adds byte to debug string in parameterIndex position.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 */
	public void setByte(int parameterIndex, byte x) throws SQLException {
		saveObject(parameterIndex, new Byte(x));
	}

	/**
	 * Adds byte[] to debug string in parameterIndex position.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 */
	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		saveObject(parameterIndex, (x == null ? "NULL" : "byte[] length=" + x.length));
	}

	/**
	 * Debug string prints NULL if reader is null, or adds "stream length= " +
	 * length.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 * @param length
	 *            length of InputStream
	 */
	public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
		saveObject(parameterIndex, (reader == null ? "NULL" : "<stream length= " + length + ">"));
	}

	/**
	 * Adds name of the object's class type(Clob) to the debug String. If object
	 * is null, NULL is added to debug String.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 */
	public void setClob(int i, Clob x) throws SQLException {
		saveObject(i, x);
	}

	

	/**
	 * Debug string displays date in YYYY-MM-DD HH24:MI:SS.# format.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 */
	public void setDate(int parameterIndex, java.sql.Date x) throws SQLException {
		saveObject(parameterIndex, x);
	}

	/**
	 * this implementation assumes that the Date has the date, and the calendar
	 * has the local info. For the debug string, the cal date is set to the date
	 * of x. Debug string displays date in YYYY-MM-DD HH24:MI:SS.# format.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 * @param cal
	 *            uses x to set time
	 */
	public void setDate(int parameterIndex, java.sql.Date x, Calendar cal) throws SQLException {
		cal.setTime(new java.util.Date(x.getTime()));
		saveObject(parameterIndex, cal);
	}

	/**
	 * Adds double to debug string in parameterIndex position.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 */
	public void setDouble(int parameterIndex, double x) throws SQLException {
		saveObject(parameterIndex, new Double(x));
	}

	

	/**
	 * Facade for PreparedStatement
	 */
	public void setFormatter(SqlFormatter formatter) {
		this.formatter = formatter;
	}

	

	/**
	 * Adds float to debug string in parameterIndex position.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 */
	public void setFloat(int parameterIndex, float x) throws SQLException {
		saveObject(parameterIndex, new Float(x));
	}

	/**
	 * Adds int to debug string in parameterIndex position.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 */
	public void setInt(int parameterIndex, int x) throws SQLException {
		saveObject(parameterIndex, new Integer(x));
	}

	/**
	 * Adds long to debug string in parameterIndex position.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 */
	public void setLong(int parameterIndex, long x) throws SQLException {
		saveObject(parameterIndex, new Long(x));
	}

	

	/**
	 * Adds a NULL to the debug String.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 */
	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		saveObject(parameterIndex, "NULL");
	}

	/**
	 * Adds a NULL to the debug String.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 * @param typeName
	 *            type of Object
	 */
	public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
		saveObject(parameterIndex, "NULL");
	}

	/**
	 * Adds name of the object's class type to the debug String. If object is
	 * null, NULL is added to debug String.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 */
	public void setObject(int parameterIndex, Object x) throws SQLException {
		saveObject(parameterIndex, (x == null ? "NULL" : x.getClass().getName()));
	}

	/**
	 * Adds name of the object's class type to the debug String. If object is
	 * null, NULL is added to debug String.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 * @param targetSqlType
	 *            database type
	 */
	public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
		saveObject(parameterIndex, (x == null ? "NULL" : x.getClass().getName()));
	}

	/**
	 * Adds name of the object's class type to the debug String. If object is
	 * null, NULL is added to debug String.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 * @param targetSqlType
	 *            database type
	 * @param scale
	 *            see PreparedStatement
	 */
	public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException {
		saveObject(parameterIndex, (x == null ? "NULL" : x.getClass().getName()));
	}

	

	/**
	 * From the javadocs: A reference to an SQL structured type value in the
	 * database. A Ref can be saved to persistent storage. The output from this
	 * method call in DebuggableStatement is a string representation of the Ref
	 * object by calling the Ref object's getBaseTypeName() method. Again, this
	 * will only be a String representation of the actual object being stored in
	 * the database.
	 * 
	 * @param i
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 */

	public void setRef(int i, Ref x) throws SQLException {
		saveObject(i, x);
	}

	/**
	 * Adds short to debug string in parameterIndex position.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 */
	public void setShort(int parameterIndex, short x) throws SQLException {
		saveObject(parameterIndex, new Short(x));
	}

	/**
	 * Adds String to debug string in parameterIndex position. If String is null
	 * "NULL" is inserted in debug string. note**** In situations where a single
	 * ' is in the string being inserted in the database. The debug string will
	 * need to be modified to reflect this when running the debug statement in
	 * the database.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 */
	public void setString(int parameterIndex, String x) throws SQLException {
		saveObject(parameterIndex, x);
	}

	/**
	 * Debug string displays Time in HH24:MI:SS.# format.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 */
	public void setTime(int parameterIndex, Time x) throws SQLException {
		saveObject(parameterIndex, x);
	}

	/**
	 * This implementation assumes that the Time object has the time and
	 * Calendar has the locale info. For the debug string, the cal time is set
	 * to the value of x. Debug string displays time in HH24:MI:SS.# format.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 * @param cal
	 *            sets time based on x
	 */
	public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
		cal.setTime(new java.util.Date(x.getTime()));
		saveObject(parameterIndex, cal);
	}

	/**
	 * Debug string displays timestamp in YYYY-MM-DD HH24:MI:SS.# format.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 */
	public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
		saveObject(parameterIndex, x);
	}

	/**
	 * This implementation assumes that the Timestamp has the date/time and
	 * Calendar has the locale info. For the debug string, the cal date/time is
	 * set to the default value of Timestamp which is YYYY-MM-DD HH24:MI:SS.#.
	 * Debug string displays timestamp in DateFormat.LONG format.
	 * 
	 * @param parameterIndex
	 *            index of parameter
	 * @param x
	 *            parameter Object
	 * @param cal
	 *            sets time based on x
	 */
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
		cal.setTime(new java.util.Date(x.getTime()));
		saveObject(parameterIndex, cal);
	}

	/**
	 * this toString is overidden to return a String representation of the sql
	 * statement being sent to the database. If a bind variable is missing then
	 * the String contains a ? + (missing variable #)
	 * 
	 * @return the above string representation
	 */
	public String toString() {
		StringTokenizer st = new StringTokenizer(filteredSql, "?");
		int count = 1;
		StringBuffer statement = new StringBuffer();
		while (st.hasMoreTokens()) {
			statement.append(st.nextToken());
			if (count <= variables.length) {
				if (variables[count - 1] != null && variables[count - 1].isValueAssigned()) {
					try {
						statement.append(formatter.format(variables[count - 1].getDebugObject()));
					} catch (SQLException e) {
						statement.append("SQLException");
					}
				} else {
					statement.append("? " + "(missing variable # " + count + " ) ");
				}
			}
			count++;
		}
		// unfilter the string in case there where rogue '?' in query string.
		char[] unfilterSql = statement.toString().toCharArray();
		for (int i = 0; i < unfilterSql.length; i++) {
			if (unfilterSql[i] == '\u0007')
				unfilterSql[i] = '?';
		}

		return new String(unfilterSql);

	}

	private class DebugObject {
		private Object debugObject;
		private boolean valueAssigned;

		public DebugObject(Object debugObject) {
			this.debugObject = debugObject;
			valueAssigned = true;
		}

		public Object getDebugObject() {
			return debugObject;
		}

		public boolean isValueAssigned() {
			return valueAssigned;
		}
	}
}
