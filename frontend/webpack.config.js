const MiniCss = require("mini-css-extract-plugin");
const RemovePlugin = require('remove-files-webpack-plugin');

module.exports = {
  plugins: [
    new MiniCss(),
    new RemovePlugin({
      before: {
        test: [
          {
            folder: './dist',
            method: () => true
          }
        ],
        exclude: [
          './dist/index.html',
        ]
      }
    })
  ],
  module: {
    rules: [
      {
        test: /\.css$/i,
        use: [MiniCss.loader, "css-loader"],
      },
    ],
  },
};