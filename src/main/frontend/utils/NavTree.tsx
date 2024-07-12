interface RouteItem {
  name: string;
  icon?: JSX.Element;
  route: string;
}

export interface TreeNode extends RouteItem {
  children: TreeNode[];
}

function insertRoute(tree: { [key: string]: any }, parts: string[], item: RouteItem) {
  if (parts.length === 0) return;

  const part = parts.shift()!;
  if (!tree[part]) {
    tree[part] = { children: {}, item: null };
  }

  if (parts.length === 0) {
    tree[part].item = item;
  }

  insertRoute(tree[part].children, parts, item);
}

function treeToList(tree: { [key: string]: any }): TreeNode[] {
  return Object.keys(tree)
    .filter((key) => Object.prototype.hasOwnProperty.call(tree, key))
    .map((key) => {
      const node: TreeNode = tree[key].item || { name: key, route: `/${key}`, children: [], icon: null };
      node.children = treeToList(tree[key].children);
      return node;
    });
}

export function treeBuilder(items: RouteItem[]): TreeNode[] {
  const tree: { [key: string]: any } = {};

  items.forEach((item) => {
    // Changed from map to forEach
    const routeParts = item.route.split('/').filter((part) => part);
    insertRoute(tree, routeParts, item);
  });

  return treeToList(tree);
}
