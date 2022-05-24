const HtmlWebpackPlugin = require('html-webpack-plugin');
const path = require('path');

module.exports = {
    resolve: {
        alias: {
            'vue$': 'vue/dist/vue.esm.js'
        },
        extensions: ['*', '.js', '.vue', '.json']
    },
    module: {
        rules: [
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            },
            {
                test: /\.scss$/,
                use: [
                    'vue-style-loader',
                    'css-loader',
                    {
                        loader: 'sass-loader',
                        options: {
                            implementation: require("sass"),
                            sassOptions: {
                                indentedSyntax: false
                            },
                        }
                    }
                ]
            },
            {
                test: /\.vue$/,
                loader: 'vue-loader'
            },
            {
                test: /\.(jpg|png)$/,
                use: {
                    loader: "file-loader",
                    options: {
                        name: "[path][name].[hash].[ext]"
                    }
                }
            },
            {
                test: /\.(woff(2)?|ttf|eot|svg)(\?v=\d+\.\d+\.\d+)?$/,
                type: 'asset/resource',
                dependency: {not: ['url']}
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            filename: './index.html',
            template: './src/main/webapp/index.html'
        })
    ],
    entry: {
        app: ["./src/main/webapp/js/index.js"]
    },
    output: {
        filename: '[name].[contenthash].js'
    },
    devtool: 'inline-cheap-module-source-map',
    devServer: {
        port: 8080,
        static: {
            directory: path.resolve(__dirname, "src", "main", "webapp", "public"),
            publicPath: "/",
            serveIndex: true,
            watch: true,
        },
        proxy: {
            '/': {
                // TODO: ensure this points to your zulia node (localhost or an IP or a machine name)
                target: 'http://your-zulia-node:32192',
                ws: false,
                changeOrigin: true,
                secure: false
            }
        }
    }
}