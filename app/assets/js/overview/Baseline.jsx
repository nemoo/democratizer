
import React from "react";
import mui from "material-ui";
import Router from "react-router";

const ThemeManager = new mui.Styles.ThemeManager();
const Card = mui.Card;
const CardTitle = mui.CardTitle;
const CardText = mui.CardText;
const RaisedButton = mui.RaisedButton;
const Link = Router.Link;
const Navigation = Router.Navigation;

const Baseline = React.createClass({
  mixins: [Navigation],  
  render() {    
    return (
        <div><br/>
          <Card initiallyExpanded={false}>
            <CardTitle
                title={this.props.data.name}
                showExpandableButton={true}>
                <br/>
              <RaisedButton 
                label="Democratize now!" 
                primary={true} 
                onClick={()=>this.transitionTo("voteview",{baselineId: this.props.data.baselineId})}
              />
            </CardTitle>
            <CardText expandable={true}>
                {this.props.data.description}
            </CardText>
          </Card>
        </div>
    );
  }  
});  

module.exports = Baseline;