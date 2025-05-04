/* global use, db */
// MongoDB Playground
// To disable this template go to Settings | MongoDB | Use Default Template For Playground.
// Make sure you are connected to enable completions and to be able to run a playground.
// Use Ctrl+Space inside a snippet or a string literal to trigger completions.
// The result of the last command run in a playground is shown on the results panel.
// By default the first 20 documents will be returned with a cursor.
// Use 'console.log()' to print to the debug output.
// For more documentation on playgrounds please refer to
// https://www.mongodb.com/docs/mongodb-vscode/playgrounds/

// Select the database to use.
use('test');


async function findMin(module, value) {
    const result = await module.aggregate([
      {
        $group: {
          _id: null,
          minCost: { $min: value }
        }
      }
    ]).toArray();
    
    return (result[0] && result[0].minCost != null)
    ? result
    : null;
}


const cu = db.getCollection('ComputingUnit');
findMin(cu, '$cost');

const co = db.getCollection('CoolingUnit');
findMin(co, '$cost');

const su = db.getCollection('StorageUnit');
findMin(su, '$cost'); 

const tr = db.getCollection('Transformer');
findMin(tr, '$cost');

