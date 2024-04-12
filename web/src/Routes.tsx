import { Route, Switch } from 'wouter';
import { ItemGallery } from './views/ItemGallery';
import { ItemPage } from './views/ItemPage';
import { Home } from './views/Home';

export const Routes = () => {
	return (
		<Switch>
			<Route path='item/:itemId' component={ItemPage} />

			<Route path='/items' component={ItemGallery} />

			<Route path='/' component={Home} />
		</Switch>
	);
};
