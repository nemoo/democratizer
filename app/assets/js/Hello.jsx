var HelloMessage = React.createClass({
  render: function() {
    return <div>Hello {this.props.name}, this is our overview of baselines for user1:</div>;
  }
});

React.render(<HelloMessage name="Jochen" />,
	document.getElementById('app'));