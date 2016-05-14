democratizer
============
[![Build Status](https://travis-ci.org/nemoo/democratizer.svg?branch=master)](https://travis-ci.org/nemoo/democratizer)

Improve nations of the world through educated discussion about how to distribute funds between various government functions.

Getting started
------------
This application features a javascript client written in reactjs. It uses react-router for client site routing, material-ui for material design greatness and ES6 javascript for coder delight.

The server application uses scala, play framework, slick and mysql as a restful backend.

The build process combines webpack and play activator which enables simple automatic browser refresh for server and client side code.

1. Install [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

2. Install [node.js](https://nodejs.org/)

3. Install webpack: `npm install webpack -g`

4. Install dependencies: `npm install`

5. Run `webpack --watch` for automatic recompilation of the client app.

6. Run `activator ~run` for automatic recompilation of the server app. If you want to use the activator gui, run `activator ui` for Linux and Mac or activator.bat for Windows.

Done: [http://localhost:9000/](http://localhost:9000/)
