import React from "react";
import mui from "material-ui";
import Bar from "./Bar.jsx";

import {Card, CardTitle, CardText} from 'material-ui/Card';
import LinearProgress from 'material-ui/LinearProgress';

const Categories = React.createClass({
    render() {
        return (
            <Card>
              <CardText>
                {
                  this.props.bars.map( bar => {
                    return (
                      <Bar key={bar.basevalueId} onSetDelta={this.props.onSetDelta} {...bar}/>
                      );
                  })
                }
              </CardText>
            </Card>
        );
    }
});

module.exports = Categories;