const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function (app) {
  app.use(
    createProxyMiddleware('/api/v4', {
      target: 'https://meeting.ssafy.com/',
      onProxyReq(proxyReq, req, res) {
        proxyReq.setHeader('Origin','http://localhost:3000')
      },
      changeOrigin : true
    }),

    createProxyMiddleware('/api/', {
      target: 'http://k6c104.p.ssafy.io',
      onProxyReq(proxyReq, req, res) {
        proxyReq.setHeader('Origin','http://localhost:3000')
      },
      changeOrigin : true
    })
    
  );
}