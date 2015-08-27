democratizer
============
[![Build Status](https://travis-ci.org/nemoo/democratizer.svg?branch=master)](https://travis-ci.org/nemoo/democratizer)

Improve nations of the world through educated discussion about how to distribute funds between various government functions.

How to setup the dev environment (quick guide)
------------

1. Clone our Github repo to your desired location.

2. Install JDK 7 from [http://www.oracle.com/technetwork/java/javase/downloads/index.html](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

3. Install Play Framework through Activator from [https://www.playframework.com/documentation/2.3.x/Installing](https://www.playframework.com/documentation/2.3.x/Installing).

4. Run `activator ui` for Linux and Mac (or `activator.bat` for Windows), choose "Open existing app" and navigate to the location you chose before and go to [https://www.typesafe.com/activator/docs](https://www.typesafe.com/activator/docs) if you need further instructions.
    
5. In order to use npm, install node.js from [https://nodejs.org/](https://nodejs.org/) and follow their readme for further instructions.
    
6. Use `npm install <package> --save-dev` to install the following npm packages: webpack, jsx-loader and material-ui in save dev mode and, afterwards, check if the packages react and react-tap-event-plugin have been installed automatically.
   
7. In order to transform the source jsx files and generate a bundled js file run `./node_modules/webpack/bin/webpack.js`. (Alternatively, you can install webpack in global mode using `npm install webpack -g` and simple run `webpack` in your root folder.) Also, you might want to use `webpack --watch` for automatic recompilation on change detection.