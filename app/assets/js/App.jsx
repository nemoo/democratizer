import React from "react";
import { render } from "react-dom";
import {Router, Link, hashHistory, Route, IndexRoute, withRouter} from "react-router";
import VoteView from "./voteview/Voteview.jsx";
import OverView from "./overview/Overview.jsx";
import AppBar from 'material-ui/AppBar';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';


const App = React.createClass({
  render() {
      return (
          <div>
              <AppBar
                showMenuIconButton={false}
                title="Democratizer"
                onTitleTouchTap={()=>this.props.router.push('/')}
              >
              </AppBar>
              <br/>

            {this.props.children}
          </div>
      );
  }
});

module.exports = withRouter(App);
