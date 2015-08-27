var injectTapEventPlugin = require("react-tap-event-plugin");

//Needed for onTouchTap
//Can go away when react 1.0 release
//Check this repo:
//https://github.com/zilverline/react-tap-event-plugin
injectTapEventPlugin();

var React = require('react'),
    mui = require('material-ui'),
    $ = require('jquery'),
    ThemeManager = new mui.Styles.ThemeManager(),
    Card = mui.Card,
    CardHeader = mui.CardHeader,
    CardText = mui.CardText;


var HelloMessage = React.createClass({
  childContextTypes: {
    muiTheme: React.PropTypes.object
  },

  getChildContext: function() {
    return {
      muiTheme: ThemeManager.getCurrentTheme()
    };
  },
  
  getInitialState: function() {
      return {
          baselines: []
      }
  },
  componentDidMount: function() {
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

  render: function() {
      return (
          <div>{this.state.baselines.map(function(baseline) {
              return (
                  <Baseline key={baseline.id} data={baseline} />
              );
          })}</div>
      );
  }
});

var Baseline = React.createClass({	
  render: function() {
    return (
          <Card initiallyExpanded={true}>
            <CardHeader
                title={this.props.data.name}
                subtitle="Subtitle"
                showExpandableButton={true}>
            </CardHeader>
            <CardText expandable={true}>
                {this.props.data.description}
            </CardText>
          </Card>     
    );
  }  
});  

React.render(<HelloMessage url="overview/1" />,
	document.getElementById('app'));

module.exports = HelloMessage;