const {createProxyMiddleware} = require("http-proxy-middleware");
module.exports = function (app) {
    app.use(
        "/api",
        createProxyMiddleware({
            target: "http://ec2-3-92-225-56.compute-1.amazonaws.com:8088",
            changeOrigin: true,
            pathRewrite: {'^/api': '/api'}
        })
    )

};