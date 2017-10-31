import App from './app.jsx';
import FirstPage from './first-page.jsx';
import SecondPage from './second-page.jsx';

ReactDOM.render(
	<Router history = {browserHistory}>
		<Route path = "/" component = {App}>
			<Route path = "/first" component = {FirstPage}>
			<Route path = "/second" component = {SecondPage}>
		</Route>
	</Router>
);