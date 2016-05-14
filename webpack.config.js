module.exports = {
    entry: "./app/assets/js/index.jsx",

    output: {
        path: "./public/js",
        filename: "Bundle.js"
    },

    module: {
        loaders: [
            {
                test: /\.jsx$/,
                loader: 'babel',
                exclude: /node_modules/,
                query:
                {
                   presets:['es2015', 'react']
                }
            }
        ]
    }
};