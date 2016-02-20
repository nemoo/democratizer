import React from "react";
import mui from "material-ui";

const Card = mui.Card;
const CardTitle = mui.CardTitle;
const CardText = mui.CardText;
const LinearProgress = mui.LinearProgress;
const FlatButton = mui.FlatButton;

const Bar = React.createClass({
    render() {
      const absoluteBase = this.props.basevalue;
      const percentageBase = absoluteBase / 1000;
      const absoluteDelta = this.props.basevalue + (this.props.delta || 0);
      const percentageDelta = absoluteDelta / 1000;
      return (
          <div key={this.props.basevalueId}>
             <p>{this.props.category} - {this.props.basevalue} Mio. Euro</p>             
             <LinearProgress mode="determinate" value={percentageBase} />
             <p>Mein Vorschlag: {absoluteDelta} Mio. Euro</p>
             <LinearProgress mode="determinate" value={percentageDelta} />
             <FlatButton label="plus 5 milliarden" primary={true} onClick={this.props.onSetDelta.bind(null, this.props.basevalueId, (this.props.delta || 0) + 5000)} />
             <FlatButton label="minus 5 milliarden" primary={true} onClick={this.props.onSetDelta.bind(null, this.props.basevalueId, (this.props.delta || 0) - 5000)} />             
          </div> 
      );
    }
});

module.exports = Bar;