

var Overview = React.createClass({

    getInitialState: function() {
        return {
            baselines: []
        }
    },

    componentDidMount: function() {
        $.ajax({
            url: this.props.url,
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

    render: function() {
        return <div>{this.state.baselines.map(function(b) {
            return (
                <Baseline name={b.name}>
                    {b.submitted ? "yes" : "no"}
                </Baseline>
            );
        })}</div>;
    }
});

var Baseline = React.createClass({
    render: function() {
        return <div>Baseline: {this.props.name} -- already votet?: {this.props.children} </div>;
    }
});

React.render(<Overview url="http://localhost:9000/getOverview/1" />,
    document.getElementById('overview'));