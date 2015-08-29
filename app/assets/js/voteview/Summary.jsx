import React from "react";
import mui from "material-ui";

const ThemeManager = new mui.Styles.ThemeManager(),
    Card = mui.Card,
    CardTitle = mui.CardTitle,
    CardText = mui.CardText;

const Summary = React.createClass({
    render() {
        return (
            <Card>
              <CardTitle title={this.props.data.name} />
              <CardText>
                Verfügbares Budget: {this.props.data.revenue} Mio. Euro
                <br/>
                Ihr Vorschlag zu den Staatsausgaben: {this.props.data.revenue + 5000} Mio. Euro
              </CardText>
            </Card>
        );
    }
});

module.exports = Summary;