democratizer
============
[![Build Status](https://travis-ci.org/nemoo/democratizer.svg?branch=master)](https://travis-ci.org/nemoo/democratizer)

Improve nations of the world through educated discussion about how to distribute funds between various government functions.

Getting started
------------

1. Install [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

2. Install [node.js](https://nodejs.org/)

3. Run `activator ui` for Linux and Mac (or `activator.bat` for Windows), choose "Open existing app" and navigate to the location you chose before and go to [https://www.typesafe.com/activator/docs](https://www.typesafe.com/activator/docs) if you need further instructions.  
    
4. Install the following npm packages and, afterwards, check if the packages react and react-tap-event-plugin have been installed automatically.
```
npm install webpack --save-dev
npm install jsx-loader --save-dev
npm install material-ui --save-dev
npm install jquery --save-dev
```
5. In order to transform the source jsx files and generate a bundled js file run `./node_modules/webpack/bin/webpack.js`. (Alternatively, you can install webpack in global mode using `npm install webpack -g` and simple run `webpack` in your root folder.) Also, you might want to use `webpack --watch` for automatic recompilation on change detection.