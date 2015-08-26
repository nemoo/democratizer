module.exports = {
    entry: "./app/assets/js/Hello.jsx",

    output: {
        path: "./public/js",
        filename: "Bundle.js"
    },

    module: {
        loaders: [
            {
                test: /\.jsx$/,
                loader: 'jsx-loader',
                exclude: /node_modules/
            }
        ]
    }
};