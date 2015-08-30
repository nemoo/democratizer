
import React from "react";
import $ from "jquery";
import Summary from "./Summary.jsx"
import Categories from "./Categories.jsx"

const VoteView = React.createClass({
    getInitialState() {
        return {  
        bars: []
        }
    },
    componentDidMount() {
        $.ajax({
            url: "voteview/1/1",
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
    render() {
        return (
            <div>
              <Summary {...this.state} />
              <Categories {...this.state} />
            </div>            
        );
    }
});

module.exports = VoteView;