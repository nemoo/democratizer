
import React from "react";
import mui from "material-ui";
import Router from "react-router";

const ThemeManager = new mui.Styles.ThemeManager();
const Card = mui.Card;
const CardTitle = mui.CardTitle;
const CardText = mui.CardText;
const RaisedButton = mui.RaisedButton;
const Link = Router.Link;

const Baseline = React.createClass({
  handleVotebutton(page) {
    this.props.onVotebutton(page);
  },
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
                  onClick={this.handleVotebutton.bind(this, "voteview")} 
                />
              <Link to="voteview" params={{baselineId: this.props.data.id}}>Democratize now</Link>
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