import { Route, Switch } from 'wouter';
import { ItemGallery } from './views/ItemGallery';
import { ItemPage } from './views/ItemPage';
import { Home } from './views/Home';
import GettingStarted from './views/GettingStarted';
import TechnologyPage from './views/TechnologyPage';
import AboutPage from './views/AboutPage';

export const Routes = () => {
	return (
		<Switch>
			<Route path='item/:itemId' component={ItemPage} />

			<Route path='/items' component={ItemGallery} />

			<Route path='/getting_started' component={GettingStarted} />

			<Route path='/about' component={AboutPage} />

			<Route path='/technology' component={TechnologyPage} />

			<Route path='/' component={Home} />
		</Switch>
	);
};
