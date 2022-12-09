const {createProxyMiddleware} = require("http-proxy-middleware");
module.exports = function (app) {
    app.use(
        "/api",
        createProxyMiddleware({
            target: "http://easygarage.link:8088",
            changeOrigin: true,
            // pathRewrite: {'^/api': '/api'},
            onProxyReq(proxyReq, req) {
                // 将本地请求的头信息复制一遍给代理。
                // 包含cookie信息，这样就能用登录后的cookie请求相关资源
                Object.keys(req.headers).forEach(function (key) {
                    proxyReq.setHeader(key, req.headers[key])
                })
                // 代理的host 设置成被代理服务的，解决跨域访问
                // proxyReq.setHeader('Host', "http://ec2-3-92-225-56.compute-1.amazonaws.com:8088")
            },
            onProxyRes(proxyRes, req, res) {
                // 将服务器返回的头信息，复制一遍给本地请求的响应。
                // 这样就能实现 执行完登录后，本地的返回请求中也有相关cookie，从而实现登录功能代理。
                Object.keys(proxyRes.headers).forEach(function (key) {
                    res.append(key, proxyRes.headers[key])
                })
            }
        })
    )

};