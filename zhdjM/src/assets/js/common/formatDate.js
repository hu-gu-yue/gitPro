/**
 * Created by hlf on 2017/11/8.
 */
import {dateFormat} from 'vue'

class format {
    dateFormat1 (date,type) {
        if(type == 'YMD'){
            date = dateFormat(date, 'YYYY-MM-DD');
        } else if (type == 'HMS'){
            date = dateFormat(date, 'HH:mm:ss');
        } else {
            date = dateFormat(date, 'YYYY-MM-DD HH:mm:ss');
        }
        return date;
    }
}

export default  format
