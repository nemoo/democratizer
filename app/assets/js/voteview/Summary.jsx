import React from "react";
import mui from "material-ui";

import {Card, CardTitle, CardText} from 'material-ui/Card';
import RaisedButton from 'material-ui/RaisedButton';


const Summary = React.createClass({
    render() {
      let originalRevenue;
      if (typeof this.props.bars[0] !== 'undefined') { 
        originalRevenue = this.props.bars.reduce((a,b) => {
          return a + (b.basevalue);
        }, 0);
      } else {
        originalRevenue = 0;
      }  
      let userRevenue;
      if (typeof this.props.bars[0] !== 'undefined') { 
        userRevenue = this.props.bars.reduce((a,b) => {
          return a + (b.basevalue + (b.delta || 0));
        }, 0);
      } else {
        userRevenue = 0;
      }        
      return (
          <Card>
            <CardTitle title={this.props.name} />
            <CardText>
              <p>
                Verfügbares Budget: {originalRevenue} Mio. Euro
              </p>
              <p>
                Ihr Vorschlag zu den Staatsausgaben: {userRevenue} Mio. Euro
              </p>              
              <RaisedButton label="variante speichern" primary={true}  onClick={this.props.onSaveVote} />              
            </CardText>
          </Card>
      );
    }
});

module.exports = Summary;