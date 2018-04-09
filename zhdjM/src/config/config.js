/**
 * Created by tanxijun on 2017/9/21.
 */
/**
 * 配置编译环境和线上环境之间的切换
 *
 * baseUrl: 域名地址
 * routerMode: 路由模式
 * imgBaseUrl: 图片所在域名地址
 *
 */

let webServer = 'http://192.168.1.218:8005/'
let imageRootPath = 'https://imgt.zjyou.cn/'
let style0RootPath = 'http://192.168.1.218:8080/static/'


if (process.env.NODE_ENV === 'development') {

} else if (process.env.NODE_ENV === 'production') {
    webServer = 'https://m.zjyou.cn/yc/'
    imageRootPath = 'https://img.zjyou.cn/'
    urlHolder = 'https://m.zjyou.cn/yc/jszxM/'
}

export {
    webServer,
    imageRootPath,
    style0RootPath
}
