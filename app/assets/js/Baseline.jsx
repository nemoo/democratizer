
import React from "react";
import mui from "material-ui";

const ThemeManager = new mui.Styles.ThemeManager(),
    Card = mui.Card,
    CardHeader = mui.CardHeader,
    CardText = mui.CardText,
    FlatButton = mui.FlatButton;

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
                <br/>
                <FlatButton label="Democratize now!" primary={true} />
            </CardText>            
          </Card>     
    );
  }  
});  

module.exports = Baseline;