import React from "react";
import Overview from "./Overview.jsx";

const Page = React.createClass({
    render() {
        return (
            <div>
                {this.props.name === "overview" ?
                    <Overview url="overview/1" onVotebutton={this.props.onVotebutton}/>
                    :null}

                {this.props.name === "voteview" ?
                    <div>
                        <img src="http://i.giphy.com/b21HcSrrBu8pi.gif" />
                    </div>
                    :null}
            </div>
        );
    }
});

module.exports = Page;