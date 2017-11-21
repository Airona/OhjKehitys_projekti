/*-----global-----*/
const Component = window.React.Component;
const BrowserRouter = window.ReactRouterDOM.BrowserRouter;
const Route =  window.ReactRouterDOM.Route;
const Link =  window.ReactRouterDOM.Link;
const Prompt =  window.ReactRouterDOM.Prompt;
const Switch = window.ReactRouterDOM.Switch;
const Redirect = window.ReactRouterDOM.Redirect;

/*toastr options*/
toastr.options.positionClass = 'toast-top-left';
toastr.options.progressBar = true;

/*json sorter*/
function predicateBy(prop){
   return function(a,b){
      if( a[prop] > b[prop]){
          return 1;
      }else if( a[prop] < b[prop] ){
          return -1;
      }
      return 0;
   }
}

/*-----MAIN APP-----*/
	class App extends React.Component {
		render() {
			return (
				<div>
					<Header />
					<Main />
					<Footer />
				</div>
			);
		}
	}
	
	class ModalView extends React.Component {
		constructor(props) {
			super(props);
			this.closeModal = this.closeModal.bind(this);
			
			this.state = {
				show: false,
				imageUrl: "",
				imageStatus: "loading",
				loading: true,
			}
		}
		
		componentWillReceiveProps(newProps) {
			if (this.state.show !== newProps.show) {
				this.setState({show: newProps.show});
			}
			
			if (this.state.imageUrl !== newProps.imageUrl) {
				this.setState({imageUrl: newProps.imageUrl});
			}
		}
		
		closeModal() {
			this.props.close();
			this.setState({loading: true, imageUrl: ""});
		}
		
		handleImageLoaded() {
			this.setState({ loading: false });
		}

		handleImageErrored() {
			this.setState({ loading: false });
		}

		renderSpinner() {
			if (!this.state.loading) {
				// Render nothing if not loading 
				return null;
			}
			return (
				<span className="spinner" />
			);
		}
		
		render() {
			return(
				<div className="modalView" style={this.state.show ? {display: 'block'} : {null} } onClick={this.closeModal}>
					<span className="closeBt" onClick={this.closeModal}>&times;</span>
					<img
						className="modalView-content" 
						src={this.state.imageUrl}
						onLoad={this.handleImageLoaded.bind(this)}
						onError={this.handleImageErrored.bind(this)}
					/>
					{this.renderSpinner()}
					<div id="caption">{this.props.caption}</div>
				</div>
			);
		}
	}
	
	class ImageControl extends React.Component {
		constructor(props) {
			super(props);
			this.deleteImage = this.deleteImage.bind(this);
			this.createImage = this.createImage.bind(this);
			this.closeView = this.closeView.bind(this);
			this.openView = this.openView.bind(this);
			
			this.state = {
				images: [],
				showModalView: false,
				modalImage: "",
				modalCaption: ""
			};
		}
		
		componentDidMount() {
			this.loadImagesFromServer();
		}
		
		//Modal controls
		closeView() {
			this.setState({ showModalView: false });
		}
		openView(imageUrl, caption) {
			this.setState({ showModalView: true,
							modalImage: imageUrl,
							modalCaption: caption
			});
		}
		
		// Load images from database
		loadImagesFromServer() {
			fetch('http://localhost:8080/api/images', {credentials: 'same-origin'})
			.then((response) => response.json())
			.then((responseData) => {
				this.setState({
					images: responseData._embedded.images,
				});
			});
		}
		
		// Delete image
		deleteImage(image) {
			fetch (image._links.self.href,
				{method: 'DELETE', credentials: 'same-origin'
			}).then(
				res => this.loadImagesFromServer()
			)
		}
	  
		// Create new image
		createImage(data ,image) {
			fetch("http://localhost:8080/upload", {
				credentials: 'same-origin',
				mode: 'no-cors',
				method: "POST",
				headers: {
				"Accept": "application/json",
				"type": "formData"
				},
				body: data
			}).then(
				(response) => response.json()
			).then(
				(responseData) => {
					console.log(image);
					image {
						date: responseData._embedded.images.date,
						url: responseData._embedded.images.url
					};
					console.log(image);
				}
			).then(
				fetch('http://localhost:8080/api/images', {
					method: 'POST', credentials: 'same-origin',
					headers: {
						'Content-Type': 'application/json',
					},
					body: JSON.stringify(image)
				}).then(
					res => this.loadImagesFromServer()
				)
			).then(
				function (response) {
					if (response.ok) {
						toastr.success("OK! ");
					} else if (response.status == 401) {
						toastr.error("401 ");
					}
					}, function (e) {
						toastr.warning("Error submitting form! <br/>" + e);
					}
			);
		}
		
		render() {
			/*show only for user uploads, type=1*/
			var imageForm;
			if (this.props.type == 1){
				imageForm = <ImageForm createImage={this.createImage}/>
			}
			
			return (
				<div>
					{imageForm}
					<ImageTable
						deleteImage={this.deleteImage} 
						images={this.state.images}
						closeView={this.closeView}
						openView={this.openView}
						type={this.props.type}
					/>
					<ModalView
						show={this.state.showModalView}
						close={this.closeView}
						imageUrl={this.state.modalImage}
						caption={this.state.modalCaption}
					/>
				</div>
			);
		}
	}
		
	class ImageTable extends React.Component {
		constructor(props) {
			super(props);
		}
		
		render() {
			var updateTh;
			var deleteTh;
			if (this.props.type == 2){
				updateTh = (
					<th></th>
				);
				deleteTh = (
					<th></th>
				);
			}
			
			//Sorting
			this.props.images.sort( predicateBy("date") );
			this.props.images.sort( predicateBy("game") );
			
			var images = this.props.images.map(image =>
				<Image
					key={image._links.self.href} 
					image={image}
					deleteImage={this.props.deleteImage} 
					closeView={this.props.closeView} 
					openView={this.props.openView} 
					type={this.props.type}
				/>
			);

			return (
			<div className={(this.props.type == 1 ? 'tableDivWithForm' : '')}>
			  <table className="table">
				<thead>
				  <tr>
					<th>Game</th>
					<th>Name</th>
					<th>User</th>
					<th>Date</th>
					{updateTh}
					{deleteTh}
				  </tr>
				</thead>
				<tbody>{images}</tbody>
			  </table>
			  </div>);
		}
	}
			
	class Image extends React.Component {
		constructor(props) {
			super(props);
			this.deleteImage = this.deleteImage.bind(this);
			this.showImage = this.showImage.bind(this);
		}

		deleteImage(e) {
			e.stopPropagation();
			this.props.deleteImage(this.props.image);
		} 
		
		showImage() {
			console.log(this.props.image.url);
			this.props.openView(this.props.image.url, this.props.image.name);
		}
		
		render() {
			var updateBt;
			var deleteBt;
			
			if (this.props.type == 2){
				updateBt = (
					<td>
						<button className="btn btn-success" onClick={this.deleteImage}>Update</button>
					</td>
				);
				deleteBt = (
					<td>
						<button className="btn btn-danger" onClick={this.deleteImage}>Delete</button>
					</td>
				);
			}
			
			return (
			  <tr onClick={this.showImage}>
				<td>{this.props.image.game}</td>
				<td>{this.props.image.name}</td>
				<td>{this.props.image.user}</td>
				<td>{this.props.image.date}</td>
				{updateBt}
				{deleteBt}
			  </tr>
			);
		} 
	}

	class ImageForm extends React.Component {
		constructor(props) {
			super(props);
			this.state = {
				game: '',
				name: '',
				user: '',
				date: '',
				url: '',
				
				file: '',
				imagePreviewUrl: '',
			};
			this.handleSubmit = this.handleSubmit.bind(this);   
			this.handleChange = this.handleChange.bind(this);     
		}
		
		handleImageChange(e) {
			e.preventDefault();

			let reader = new FileReader();
			let file = e.target.files[0];

			reader.onloadend = () => {
				this.setState({
					file: file,
					imagePreviewUrl: reader.result
				});
			}

			reader.readAsDataURL(file)
		}
		
		handleChange(e) {
			this.setState(
				{[e.target.name]: e.target.value}
			);
		}    
		
		handleSubmit(e) {
			e.preventDefault();
			var data = new FormData();
			data.append("file", this.state.file);
			data.append("name", this.state.file.name);
			
			var newImage = {
				game: this.state.game,	/*user input (dropdown) or new as (text)*/
				name: this.state.name,	/*user input (text)*/
				user: this.state.user,	/*system logged user*/
				date: this.state.date,	/*system upload date*/
				url: this.state.url		/*system image upload (return url)*/
			};
			
			console.log('handle uploading-', this.state.file);
			this.props.createImage(data, newImage);        
		}
		
		render() {
			let {imagePreviewUrl} = this.state;
			let $imagePreview = null;
			if (imagePreviewUrl) {
				$imagePreview = (<img src={imagePreviewUrl} />);
			} else {
				$imagePreview = (<div className="previewText">Please select an Image for Preview</div>);
			}
			
			return (
				<div className="panel panel-default formDiv">
					<span>Upload an image</span>
					<form className="form">
							<input type="text" placeholder="Game" className="form-control"  name="game" onChange={this.handleChange}/>
							<input type="text" placeholder="Name" className="form-control" name="name" onChange={this.handleChange}/>
							<input className="fileInput" type="file" onChange={(e)=>this.handleImageChange(e)} />
							
							<button className="btn btn-success" onClick={this.handleSubmit}>Upload</button>   
							
							<div className="imgPreview">
								{$imagePreview}
							</div>    
					</form>   
				</div>
			);
		}
	}
	
	class Uploader extends React.Component {
		constructor(props) {
			super(props);
			this.state = {file: '', imagePreviewUrl: ''};
		}

		_handleSubmit(e) {
			e.preventDefault();
			var data = new FormData();
			data.append("file", this.state.file);
			data.append("name", this.state.file.name);

			fetch("http://localhost:8080/upload", {
				credentials: 'same-origin',
				mode: 'no-cors',
				method: "POST",
				headers: {
				"Accept": "application/json",
				"type": "formData"
				},
				body: data
			}).then(function (response) {
				if (response.ok) {
					toastr.success("OK! ");
				} else if (response.status == 401) {
					toastr.error("401 ");
				}
				}, function (e) {
					toastr.warning("Error submitting form!");
			});
		}

		_handleImageChange(e) {
			e.preventDefault();

			let reader = new FileReader();
			let file = e.target.files[0];

			reader.onloadend = () => {
				this.setState({
					file: file,
					imagePreviewUrl: reader.result
				});
			}

			reader.readAsDataURL(file)
		}

		render() {
			let {imagePreviewUrl} = this.state;
			let $imagePreview = null;
			if (imagePreviewUrl) {
				$imagePreview = (<img src={imagePreviewUrl} />);
			} else {
				$imagePreview = (<div className="previewText">Please select an Image for Preview</div>);
			}

			return (
				<div className="previewComponent">
					<form onSubmit={(e)=>this._handleSubmit(e)}>
						<input className="fileInput" type="file" onChange={(e)=>this._handleImageChange(e)} />
						<button className="submitButton" type="submit" onClick={(e)=>this._handleSubmit(e)}>Upload Image</button>
					</form>
					<div className="imgPreview">
						{$imagePreview}
					</div>
				</div>
			)
		}
	}
	
/*-----COMMON PAGE COMPONENTS-----*/
	/*-----Header-----*/
		class Header extends React.Component {
			render() {
				return (
					<header>
						<a href="#" id="logo"></a>
						<nav>
							<ul id="navControl">
								<li><a href="/login">login</a></li>
								<li><a href="/logout">logout</a></li>
							</ul>
							<ul id="navMain">
								<li><Link to="/">frontpage</Link></li>
								<li><Link to="/browse">browse</Link></li>
								<li><Link to="/upload">upload(user)</Link></li>
								<li><Link to="/manage">manage(admin)</Link></li>
							</ul>
						</nav>
						<div className="clear"></div>
					</header>
				);
			}
		}

	/*-----Footer-----*/
		class Footer extends React.Component {
			render() {
				return (
					<footer className="clear">
						<p>Kim Brygger (c)</p>
					</footer>
				);
			}
		}

/*-----PAGE CONTENT-----*/
	class Main extends React.Component {
		render() {
			return (
				<main>
					<Switch>
						<Route exact path='/' component={Home}/>
						<Route exact path='/browse' component={Browse}/>
						<Route exact path='/upload' component={Upload}/>
						<Route exact path='/manage' component={Manage}/>
					</Switch>
				</main>
			);
		}
	}
	/*-----Home Page-----*/
		class Home extends React.Component {
			render() {
				return (
					<div>
						<h2>Welcome</h2>
						<p>Welcome text, information of the site</p>
						<br />
						<p>Create Read Update Delete, New User</p>
					</div>
				);
			}
		}

	/*-----Browse Page-----*/
		class Browse extends React.Component {
			render() {
				return (
					<div>
						<h2>Browsing page</h2>
						<p>Open images by clicking on the table rows</p>
						<p>images all users READ ONLY</p>
						<ImageControl type={0} />
					</div>
				);
			}
		}
		
	/*-----Upload page--user---*/
		class Upload extends React.Component {
			render() {
				return (
					<div>
						<h2>Upload</h2>
						<p>Registered USER.level permissions <br />Read,Create,Delete,(Update)</p>
						<ImageControl type={1} />
					</div>
				)
			}
		}
	/*-----Manage page--admin---*/
		class Manage extends React.Component {
			render() {
				return (
					<div>
						<h2>Manage</h2>
						<p>Admin.level permissions <br />Read,Create</p>
						<ImageControl type={2} />
					</div>
				)
			}
		}
	
/*----RENDER----*/
ReactDOM.render((
	<BrowserRouter>
		<App />
	</BrowserRouter>
	), document.getElementById('root')
);
