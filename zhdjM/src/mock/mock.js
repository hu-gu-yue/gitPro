/**
 * Created by tanxijun on 2017/10/13.
 */
import Mock from 'mockjs'
import DestinationMock from './data/destinationMock'
import UserMock from './data/userMock'
import Business from './data/businessMock'


/**营地模拟请求 start**/
Mock.mock('/api/v1/lyxxh/destination/getIndexContent1', 'post', DestinationMock.testMock);
Mock.mock('/api/v1/lyxxh/destination/getIndexContent1', 'post', UserMock.testMock);

Mock.mock('/api/v1/lyxxh/destination/index', 'post', DestinationMock.destinationIndex);
Mock.mock('/api/v1/lyxxh/destination/content', 'post', DestinationMock.destinationContent);
Mock.mock('/api/v1/lyxxh/resource/stay', 'post', DestinationMock.getStay);
Mock.mock('/api/v1/lyxxh/destination/getSpecial', 'post', DestinationMock.getSpecial);
Mock.mock('/api/v1/lyxxh/destination/getFood', 'post', DestinationMock.getFood);
Mock.mock('/api/v1/lyxxh/destination/getScenic', 'post', DestinationMock.getScenic);
Mock.mock('/api/v1/lyxxh/destination/getPlay', 'post', DestinationMock.getPlay);

/**营地模拟请求 end**/


//---------------------------------------------------------------------------------------------------------------------------------------//


/**用户模拟请求 start**/
Mock.mock('/api/v1/lyxxh/user/getUserCampOrder', 'post', UserMock.orderMock);

Mock.mock('/api/v1/lyxxh/user/getCampOrderDetail', 'post', UserMock.orderDetail);


Mock.mock('/api/v1/lyxxh/user/getUserCart', 'post', UserMock.getUserCart);
Mock.mock('/api/v1/lyxxh/user/jiesuanUserCart', 'post', UserMock.jiesuanUserCart);
Mock.mock('/api/v1/lyxxh/user/getUserOrder', 'post', UserMock.getUserOrder);
Mock.mock('/api/v1/lyxxh/user/getContacts', 'post', UserMock.getContacts);
Mock.mock('/api/v1/lyxxh/user/setDefaultContact', 'post', UserMock.setDefaultContact);
Mock.mock('/api/v1/lyxxh/user/deleteContact', 'post', UserMock.deleteContact);
Mock.mock('/api/v1/lyxxh/user/saveAddress', 'post', UserMock.saveAddress);
Mock.mock('/api/v1/lyxxh/user/weixinPay', 'post', UserMock.weixinPay);


Mock.mock('/api/v1/lyxxh/user/getCampOrderItems', 'post', UserMock.getCampOrderItems);
Mock.mock('/api/v1/lyxxh/user/incomeItems', 'post', UserMock.incomeItems);
Mock.mock('/api/v1/lyxxh/user/cashItems', 'post', UserMock.cashItems);

/**用户模拟请求 end**/


/**商户模拟请求 start**/
Mock.mock('/api/v1/lyxxh/business/getBusinessIndex', 'post', Business.getBusinessIndex);
Mock.mock('/api/v1/lyxxh/business/getAllGoods', 'post', Business.getAllGoods);
Mock.mock('/api/v1/lyxxh/business/getBusinessStay', 'post', Business.getBusinessStay);
Mock.mock('/api/v1/lyxxh/business/getBusinessFood', 'post', Business.getBusinessFood);
Mock.mock('/api/v1/lyxxh/business/getBusinessPlay', 'post', Business.getBusinessPlay);
Mock.mock('/api/v1/lyxxh/business/getBusinessSpecial', 'post', Business.getBusinessSpecial);

Mock.mock('/api/v1/lyxxh/resource/detail', 'post', Business.businessDetail);
/**商户模拟请求 end**/
