// Workaround for https://github.com/facebook/create-react-app/issues/5280
// Once fixed, please delete file and replace with config in package.json according to docs

const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = app => { app.use(createProxyMiddleware('ws://localhost:7000/ws'))}

