//Needed for onTouchTap
//Can go away when react 1.0 release
//Check this repo:
//https://github.com/zilverline/react-tap-event-plugin
import injectTapEventPlugin from "react-tap-event-plugin";
injectTapEventPlugin();

import React from "react";
import mui from "material-ui";
import Page from "./Page.jsx";

const ThemeManager = new mui.Styles.ThemeManager(),
    AppBar = mui.AppBar;

const App = React.createClass({
  childContextTypes: {
    muiTheme: React.PropTypes.object
  },
  getChildContext() {
    return {
      muiTheme: ThemeManager.getCurrentTheme()
    };
  },
  getInitialState() {
    return {
        pagename: "voteview"
    };
  },
  handleTransition(page) {
    this.setState({pagename: page});
  },
  render() {
      return (
          <div>
            <AppBar
              title="Democratizer"
              iconClassNameRight="muidocs-icon-navigation-expand-more" />
              <br/>
            <Page name={this.state.pagename} onVotebutton={this.handleTransition} />
          </div>
      );
  }
});

React.render(<App />,
	document.getElementById('app'));

module.exports = App;