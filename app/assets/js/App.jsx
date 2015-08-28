var injectTapEventPlugin = require("react-tap-event-plugin");

//Needed for onTouchTap
//Can go away when react 1.0 release
//Check this repo:
//https://github.com/zilverline/react-tap-event-plugin
injectTapEventPlugin();

import React from "react";
import mui from "material-ui";
import $ from "jquery";
import Baseline from "./Baseline.jsx";

const ThemeManager = new mui.Styles.ThemeManager(),
    Card = mui.Card,
    CardHeader = mui.CardHeader,
    CardText = mui.CardText;


var App = React.createClass({
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
          baselines: []
      }
  },
  componentDidMount() {
      $.ajax({
          url: this.props.url,
          dataType: 'json',
          cache: false,
          success: function(data) {
              this.setState({baselines: data});
          }.bind(this),
          error: function(xhr, status, err) {
              console.error(this.props.url, status, err.toString());
          }.bind(this)
      });
  },  

  render() {
      return (
          <div>{this.state.baselines.map(function(baseline) {
              return (
                  <Baseline key={baseline.id} data={baseline} />
              );
          })}</div>
      );
  }
});

React.render(<App url="overview/1" />,
	document.getElementById('app'));

module.exports = App;