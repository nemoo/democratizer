import React from "react";
import Overview from "./Overview.jsx";
import VoteView from "./voteview/VoteView.jsx";

const Page = React.createClass({
    render() {
        return (
            <div>
                {this.props.name === "overview" ?
                    <Overview url="overview/1" onVotebutton={this.props.onVotebutton}/>
                    :null}

                {this.props.name === "voteview" ?
                    <VoteView />
                    :null}
            </div>
        );
    }
});

module.exports = Page;