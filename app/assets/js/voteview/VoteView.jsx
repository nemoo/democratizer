
import React from "react";
import $ from "jquery";
import Summary from "./Summary.jsx"
import Categories from "./Categories.jsx"

const Voteview = React.createClass({
    contextTypes: {
      router: React.PropTypes.func
    },  
    getInitialState() {
        return { 
          baselineId: 0,
          name: "",
          revenue: 0,   
          bars: []        
        }
    },
    componentDidMount() {
        $.ajax({
            type: "GET",
            url: "voteview/" + this.context.router.getCurrentParams().baselineId,
            dataType: 'json',
            cache: false,
            success: function(data) {
                this.setState(data);
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    },
    setDelta(basevalueId, delta) {      
        const newBars = this.state.bars.map(bar => {
          if (bar.basevalueId == basevalueId) {
            bar.delta = delta;
          }  
          return bar;
        });
        this.setState({bars: newBars});        
    },
    saveVote() {
      $.ajax({
          type: "POST",
          url: "submit",
          data: JSON.stringify(this.state),
          dataType: 'json',
          contentType: "text/json; charset=utf-8",
          cache: false,
          success: function(msg) {
            console.log(msg);
          }.bind(this),
          error: function(xhr, status, err) {
              console.error(this.props.url, status, err.toString());
          }.bind(this)
      });      
    },
    render() {
        return (
            <div>
              <Summary {...this.state} onSaveVote={this.saveVote}/>
              <Categories {...this.state} onSetDelta={this.setDelta} />
            </div>            
        );
    }
});

module.exports = Voteview;