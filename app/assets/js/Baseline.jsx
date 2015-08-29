
import React from "react";
import mui from "material-ui";

const ThemeManager = new mui.Styles.ThemeManager(),
    Card = mui.Card,
    CardTitle = mui.CardTitle,
    CardText = mui.CardText,
    RaisedButton = mui.RaisedButton;

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
                <RaisedButton label="Democratize now!" primary={true} onClick={this.handleVotebutton.bind(this, "voteview")} />
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