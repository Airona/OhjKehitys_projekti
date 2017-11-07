import React, { Component } from 'react';
import { Link } from 'react-router-dom'

class Header extends Component {
	render() {
		return (
			<header>
				<a href="#" id="logo"></a>
				<nav>
					<ul id="navControl">
						<li><Link to="/login">login</Link></li>
						<li><Link to="/add">add</Link></li>
						<li><Link to="/manage">manage</Link></li>
					</ul>
					<ul id="navMain">
						<li><Link to="/">frontpage</Link></li>
						<li><Link to="/albums">albums</Link></li>
						<li><Link to="/latest">latest images</Link></li>
					</ul>
				</nav>
				<div className="clear"></div>
			</header>
		);
	}
}

export default Header;