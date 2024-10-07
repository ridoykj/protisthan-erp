import React, { useState } from 'react';

type Column<T> = {
  label: string;
  key: keyof T;
  render?: (value: T) => React.ReactNode;
};

type DataTableProps<T> = {
  rowId: string;
  data: T[];
  columns: Column<T>[];
  pageSize?: number;
};

type SortConfig<T> = {
  key: keyof T;
  direction: 'ascending' | 'descending';
};

const DataTable = <T,>({ rowId, data, columns, pageSize = 5 }: DataTableProps<T>) => {
  const [currentPage, setCurrentPage] = useState(1);
  const [sortConfig, setSortConfig] = useState<SortConfig<T> | null>(null);

  const sortedData = React.useMemo(() => {
    if (sortConfig !== null) {
      return [...data].sort((a, b) => {
        if (a[sortConfig.key] < b[sortConfig.key]) {
          return sortConfig.direction === 'ascending' ? -1 : 1;
        }
        if (a[sortConfig.key] > b[sortConfig.key]) {
          return sortConfig.direction === 'ascending' ? 1 : -1;
        }
        return 0;
      });
    }
    return data;
  }, [data, sortConfig]);

  const handleSort = (key: keyof T) => {
    let direction: 'ascending' | 'descending' = 'ascending';
    if (sortConfig && sortConfig.key === key && sortConfig.direction === 'ascending') {
      direction = 'descending';
    }
    setSortConfig({ key, direction });
  };

  const paginatedData = React.useMemo(() => {
    const startIndex = (currentPage - 1) * pageSize;
    return sortedData.slice(startIndex, startIndex + pageSize);
  }, [currentPage, sortedData, pageSize]);

  const pageCount = Math.ceil(data.length / pageSize);

  return (
    <div className="overflow-x-auto">
      <table className="min-w-full bg-white border border-gray-300">
        <thead>
          <tr>
            {columns.map((column) => (
              <th
                key={String(column.key)}
                onClick={() => handleSort(column.key)}
                className="py-2 px-4 text-left bg-gray-100 cursor-pointer"
              >
                <div className=" inline-flex gap-1">
                  {column.label}
                  {sortConfig?.key === column.key && <span>{sortConfig.direction === 'ascending' ? ' ▲' : ' ▼'}</span>}
                </div>
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {paginatedData.map((row) => (
            <tr key={`dtrow-${String(row[rowId as keyof T])}`} className="border-t border-gray-300">
              {columns.map((column) => (
                <td key={`dtdata-${String(column.key)}`} className="py-2 px-4">
                  {column.render ? column.render(row) : String(row[column.key])}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
      <div className="flex justify-between items-center mt-4">
        <button
          type="button"
          onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 1))}
          disabled={currentPage === 1}
          className="py-1 px-3 bg-blue-500 text-white disabled:opacity-50 rounded-full"
        >
          Previous
        </button>
        <span>
          Page {currentPage} of {pageCount}
        </span>
        <button
          type="button"
          onClick={() => setCurrentPage((prev) => Math.min(prev + 1, pageCount))}
          disabled={currentPage === pageCount}
          className="py-1 px-3 bg-blue-500 text-white disabled:opacity-50 rounded-full"
        >
          Next
        </button>
      </div>
    </div>
  );
};

export default DataTable;
