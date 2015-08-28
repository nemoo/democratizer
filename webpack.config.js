module.exports = {
    entry: "./app/assets/js/App.jsx",

    output: {
        path: "./public/js",
        filename: "Bundle.js"
    },

    module: {
        loaders: [
            {
                test: /\.jsx$/,
                loader: 'babel',
                exclude: /node_modules/
            }
        ]
    }
};