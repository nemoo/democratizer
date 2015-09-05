
import React from "react";
import $ from "jquery";
import Baseline from "./Baseline.jsx";

const Overview = React.createClass({
    getInitialState() {
        return {
            baselines: []
        }
    },
    componentDidMount() {
        $.ajax({
            url: "/overview",
            dataType: 'json',
            cache: false,
            success: function(data) {
                this.setState({baselines: data});
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    },
    render() {
        return (
            <div>{this.state.baselines.map(function(baseline) {
                return (
                    <Baseline key={baseline.id} data={baseline} />
                );
                })
            }</div>
        );
    }
});

module.exports = Overview;