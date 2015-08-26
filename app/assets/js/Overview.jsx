

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
        return (
            <div>
            <BaselineList baselines={this.state.baselines} />
            </div>

        );
    }
});

var BaselineList = React.createClass({
    render: function() {
        return (
            <div>{this.props.baselines.map(function(b) {
                return (
                    <Baseline name={b.name} description={b.description} submitted={b.submitted}/>
                );
            })}</div>
        );
    }
});

var Baseline = React.createClass({
    render: function() {
        return (
            <div></div>
        );
    }
});



React.render(<Overview url="http://localhost:9000/getOverview/1" />,
    document.getElementById('overview'));