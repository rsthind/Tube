//
//  ViewAppointmentsTableViewController.swift
//  TubeIOS
//
//  Created by Anshul Ahluwalia on 1/11/20.
//  Copyright © 2020 Anshul Ahluwalia. All rights reserved.
//

import UIKit
import FirebaseDatabase
import FirebaseAuth

class ViewAppointmentsTableViewController: UITableViewController {
    public var requests: [Request] = [Request]()
    public var requestStrings: [String] = [String]()
    override func viewDidLoad() {
        super.viewDidLoad()
        self.tableView.register(UITableViewCell.self, forCellReuseIdentifier: "requestCell")
        User.ref.child("requests").child("Matched Requests").observeSingleEvent(of: .value) { (snapshot) in
            let value = snapshot.value as? NSDictionary
            print(value)
            for key in (value?.allKeys)! {
                let requestDictionary = value?[key] as? NSDictionary ?? [:]
                //print(requestDictionary)
                for requestKey in (requestDictionary.allKeys) {
                    let request = requestDictionary[requestKey] as? NSDictionary ?? [:]
                    let requestTutorUID = request["tutorUID"] as? String ?? ""
                    if (requestTutorUID == Auth.auth().currentUser?.uid) {
                        let className = request["className"] as? String ?? ""
                        let dateTime = request["dateTime"] as? String ?? ""
                        let description = request["description"] as? String ?? ""
                        let userUID = request["userUID"] as? String ?? ""
                        let requestObject = Request(className: className, dateTime: dateTime, description: description, userUID: userUID)
                        let requestString = className + ": " + dateTime
                        self.requests.append(requestObject)
                        self.requestStrings.append(requestString)
                    }
                }
            }
            self.tableView?.reloadData()
        }

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return self.requestStrings.count
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "requestCell", for: indexPath)
        cell.textLabel?.text = requestStrings[indexPath.row]
        // Configure the cell...
        return cell
    }
    /*
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "reuseIdentifier", for: indexPath)

        // Configure the cell...

        return cell
    }
    */

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
