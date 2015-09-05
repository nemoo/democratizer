import React from "react";
import mui from "material-ui";
import Bar from "./Bar.jsx";

const ThemeManager = new mui.Styles.ThemeManager(),
    Card = mui.Card,
    CardTitle = mui.CardTitle,
    CardText = mui.CardText,
    LinearProgress = mui.LinearProgress;

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