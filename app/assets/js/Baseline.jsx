
import React from "react";
import mui from "material-ui";

const ThemeManager = new mui.Styles.ThemeManager(),
    Card = mui.Card,
    CardHeader = mui.CardHeader,
    CardText = mui.CardText;

const Baseline = React.createClass({	
  render() {    
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

module.exports = Baseline;