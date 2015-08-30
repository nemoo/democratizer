//Needed for onTouchTap
//Can go away when react 1.0 release
//Check this repo:
//https://github.com/zilverline/react-tap-event-plugin
import injectTapEventPlugin from "react-tap-event-plugin";
injectTapEventPlugin();

import React from "react";
import mui from "material-ui";
import Router from "react-router";
import VoteView from "./voteview/VoteView.jsx";
import OverView from "./overview/OverView.jsx";

const ThemeManager = new mui.Styles.ThemeManager();
const AppBar = mui.AppBar;
const RouteHandler = Router.RouteHandler;
const Route = Router.Route;
const Link = Router.Link;
const DefaultRoute = Router.DefaultRoute;
    

const App = React.createClass({
  childContextTypes: {
    muiTheme: React.PropTypes.object
  },
  getChildContext() {
    return {
      muiTheme: ThemeManager.getCurrentTheme()
    };
  },
  render() {
      return (
          <div>
            <AppBar
              title="Democratizer"
              iconClassNameRight="muidocs-icon-navigation-expand-more" />
              <br/>
            <Link to="app">Overview</Link>              
            <RouteHandler />
          </div>
      );
  }
});

var routes = (
  <Route name="app" path="/" handler={App}>
    <Route name="voteview" path="voteview/:baselineId" handler={VoteView}/>
    <DefaultRoute handler={OverView}/>
  </Route>
);

Router.run(routes, function (Handler) {
  React.render(<Handler/>, document.getElementById('app'));
});

module.exports = App;