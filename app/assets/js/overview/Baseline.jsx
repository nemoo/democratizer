
import React from "react";
import {Router, Link, hashHistory} from "react-router";
import {Card, CardTitle, CardText} from 'material-ui/Card';
import RaisedButton from 'material-ui/RaisedButton';
import { withRouter } from 'react-router'

const Baseline = React.createClass({
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
                onClick={()=>this.props.router.push(`/voteview/${this.props.data.baselineId}`)}
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

module.exports = withRouter(Baseline);
