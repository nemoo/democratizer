import React from "react";
import mui from "material-ui";

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
                {this.props.bars.map( bar => {
                  const percentage = bar.basevalue / 1000;
                  return( 
                   <div key={bar.basevalueId}>
                     <p>{bar.category} - {bar.basevalue} Mio. Euro</p>
                     <LinearProgress mode="determinate" value={percentage} />
                   </div>
                 );
                })} 
              </CardText>
            </Card>
        );
    }
});

module.exports = Categories;