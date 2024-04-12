import { Route, Switch } from 'wouter';
import { ItemGallery } from './views/ItemGallery';
import { ItemPage } from './views/ItemPage';

export const Routes = () => {
	return (
		<Switch>
			<Route path='item/:itemId' component={ItemPage} />

			<Route path='/' component={ItemGallery} />
		</Switch>
	);
};
