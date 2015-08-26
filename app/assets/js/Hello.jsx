var injectTapEventPlugin = require("react-tap-event-plugin");

//Needed for onTouchTap
//Can go away when react 1.0 release
//Check this repo:
//https://github.com/zilverline/react-tap-event-plugin
injectTapEventPlugin();

var React = require('react'),
    mui = require('material-ui'),
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

  render: function() {
    return (
        <Card initiallyExpanded={true}>
          <CardHeader
              title="Title"
              subtitle="Subtitle"
              showExpandableButton={true}>
          </CardHeader>
          <CardText expandable={true}>
              Minions ipsum belloo! Poulet tikka masala pepete chasy chasy belloo! Gelatooo bappleees po kass. Uuuhhh poopayee underweaaar belloo! Bananaaaa tatata bala tu underweaaar. Chasy jeje tatata bala tu ti aamoo! Baboiii me want bananaaa! Hana dul sae belloo! Hana dul sae me want bananaaa! Wiiiii jeje belloo! Tulaliloo jiji. Bappleees la bodaaa jiji bee do bee do bee do tulaliloo para tú. Poopayee chasy jeje bananaaaa potatoooo tatata bala tu chasy. Jiji chasy bappleees bananaaaa belloo! Butt la bodaaa wiiiii wiiiii la bodaaa tank yuuu! Tatata bala tu chasy uuuhhh la bodaaa.
          </CardText>
        </Card>
    );
  }
});

React.render(<HelloMessage name="user" />,
	document.getElementById('app'));

module.exports = HelloMessage;