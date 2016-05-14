import React from "react";
import { render } from "react-dom";
import {Router, Link, hashHistory, Route, IndexRoute, withRouter} from "react-router";
import VoteView from "./voteview/Voteview.jsx";
import OverView from "./overview/Overview.jsx";
import App from "./App.jsx";
import AppBar from 'material-ui/AppBar';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import injectTapEventPlugin from 'react-tap-event-plugin';

//Needed for onTouchTap
//Can go away when react 1.0 release
//Check this repo:
//https://github.com/zilverline/react-tap-event-plugin
injectTapEventPlugin();

render((
  <MuiThemeProvider muiTheme={getMuiTheme()}>
    <Router history={hashHistory}>
      <Route path="/" component={App}>
          <Route path="voteview/:baselineId" component={VoteView}/>
          <IndexRoute component={OverView}/>
      </Route>
    </Router>
  </MuiThemeProvider>
  ), document.getElementById('app'));
