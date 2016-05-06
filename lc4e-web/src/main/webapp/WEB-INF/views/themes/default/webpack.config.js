/**
 * Created by teddyzhu on 15/12/6.
 */
var webpack = require('webpack');

var vue = require("vue-loader");

//混淆压缩
var uglifyJsPlugin = webpack.optimize.UglifyJsPlugin;

//独立样式文件
var ExtractTextPlugin = require("extract-text-webpack-plugin");

var plugins = [
    //会将所有的样式文件打包成一个单独的style.css
    new ExtractTextPlugin("style.css", {
        disable: false
        //allChunks: true  //所有独立样式打包成一个css文件
    })
];


//压缩
plugins.push(new webpack.optimize.UglifyJsPlugin({compress: {warnings: false}}));


module.exports = {
    entry: './src/app.js',
    output: {
        path: '../../../../themes/default',
        publicPath: '${Theme}/',
        chunkFilename: "app.min.js",
        filename: 'app.js'
    },
    module: {
        loaders: [
            {
                test: /\.vue$/,
                loader: 'vue'
            },
            {
                test: /\.css$/,
                loader: ExtractTextPlugin.extract("style-loader", "css-loader?sourceMap!postcss-loader")
            },
            {test: /\.js$/, loader: 'babel', exclude: /node_modules/, query: {compact: false}},
            {
                test: /\.(png|jpg|gif)$/,
                loader: require.resolve("url-loader") + '?limit=10&name=./images/pack/[name].[ext]'
            },
            {test: /\.woff$/, loader: "url?limit=10000&minetype=application/font-woff"},
            {test: /\.(ttf|eot|svg)$/, loader: "file"}
        ]
    },
    vue: {
        css: ExtractTextPlugin.extract("style-loader",
            "css-loader?sourceMap!postcss-loader")
    },
    cssnext: {
        browsers: "last 2 versions"
    },
    resolve: {
        extensions: ['', '.js', '.vue']
    },
    plugins: plugins,
    devtool: '#source-map'
};
