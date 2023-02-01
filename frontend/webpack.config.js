// noinspection JSUnresolvedFunction

const MiniCss = require("mini-css-extract-plugin");
const RemovePlugin = require("remove-files-webpack-plugin");
const webpack = require("webpack");

module.exports = {
  plugins: [
    new webpack.ProvidePlugin({
      $: "jquery",
      jQuery: "jquery",
    }),
    new MiniCss(),
    new RemovePlugin({
      before: {
        test: [
          {
            folder: "./dist",
            method: () => true
          }
        ],
        exclude: [
          "./dist/index.html",
          "./dist/favicon",
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