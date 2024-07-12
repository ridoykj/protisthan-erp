import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import React from 'react';

export const config: ViewConfig = {
  menu: {
    title: 'Users',
    icon: 'users',
  },
};

const Index: React.FC = () => {
  return (
    <div className="flex bg-green-800 justify-center">
      <h1>User Page </h1>
      <p> This is a simple user page. </p>
    </div>
  );
};

export default Index;
